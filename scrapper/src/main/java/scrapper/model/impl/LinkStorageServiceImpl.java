package scrapper.model.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import scrapper.controllers.errors.ClientException;
import scrapper.domain.ChatRepository;
import scrapper.domain.LinkRepository;
import scrapper.domain.entity.Chat;
import scrapper.domain.entity.Link;
import scrapper.model.LinkStorageService;
import scrapper.model.dto.LinkDTO;
import scrapper.model.dto.MapperEntityWithDTO;

import java.time.OffsetDateTime;
import java.time.temporal.TemporalUnit;
import java.util.function.Consumer;

@Service
@Slf4j
public class LinkStorageServiceImpl implements LinkStorageService {

    private final LinkRepository linkRepository;
    private final ChatRepository chatRepository;
    private final MapperEntityWithDTO mapper;

    public LinkStorageServiceImpl(LinkRepository linkRepository,
                                  ChatRepository chatRepository,
                                  MapperEntityWithDTO mapper) {
        this.linkRepository = linkRepository;
        this.chatRepository = chatRepository;
        this.mapper = mapper;
    }

    @Override
    public void addLink(LinkDTO linkDTO) {
        Link link = validateLink(linkDTO);
        linkRepository.add(link);
        log.info("Added link: {}", linkDTO);
    }

    private Link validateLink(LinkDTO linkDTO) {
        Chat chat = null;
        try {
            chat = chatRepository.findByChatId(linkDTO.getChat().getChatId());
        } catch (EntityNotFoundException ignored) {
        }

        if (chat == null) {
            throw new ClientException(HttpStatus.BAD_REQUEST.value(), "Not registered yet");
        }
        if (chat.getLinks() != null && chat.getLinks().size() >= 1024) {
            throw new ClientException(HttpStatus.BAD_REQUEST.value(), "Too many links. Available links per 1024");
        }
        if (chat.getLinks() != null && chat.getLinks().stream().anyMatch(it -> it.getUrl().equals(linkDTO.getUrl()))) {
            throw new ClientException(HttpStatus.BAD_REQUEST.value(), "You are already tracked link with this url");
        }
        if (linkDTO.getUrl().isEmpty()) {
            throw new ClientException(HttpStatus.BAD_REQUEST.value(), "Empty link URL");
        }
        if (linkDTO.getUrl().length() > 512) {
            throw new ClientException(HttpStatus.BAD_REQUEST.value(), "URL too long. Max 255 characters");
        }

        Link link = mapper.getLink(linkDTO);
        link.setChat(chat);
        return link;
    }

    @Override
    public void removeLink(LinkDTO linkDTO) {
        Link link = linkRepository.findByUlrAndChatId(linkDTO.getUrl(), linkDTO.getChat().getChatId());
        if (link == null) {
            throw new ClientException(HttpStatus.BAD_REQUEST.value(), "Links is not tracked");
        }
        linkRepository.remove(link);
    }

    @Override
    public void setCheckFieldToNowForEveryLinkWithUrl(LinkDTO linkDTO) {
        makeChangeForEveryLinkWithUrl(linkDTO, (l) -> l.setCheckedAt(OffsetDateTime.now()));
    }

    @Override
    public void setUpdateFieldToValueForEveryLinkWithUrl(LinkDTO linkDTO, OffsetDateTime time) {
        makeChangeForEveryLinkWithUrl(linkDTO, (l) -> l.setUpdatedAt(time));
    }

    private void makeChangeForEveryLinkWithUrl(LinkDTO linkDTO, Consumer<Link> func) {
        int page = 0, totalPage;
        int size = 501;
        do {
            Pageable pageable = PageRequest.of(page, size);
            Page<Link> links = linkRepository.findAllByUrl(linkDTO.getUrl(), pageable);
            totalPage = links.getTotalPages();

            links.stream().parallel().forEach(link -> {
                func.accept(link);
                linkRepository.update(link);
            });

            page++;
        } while (page < totalPage);
    }

    @Override
    public Page<LinkDTO> findUniqueUrlWhatNotCheckedForALongTime(int amount, TemporalUnit temporalUnit, Pageable pageable) {
        Page<Link> page = linkRepository.findUniqueUrlWhatNotCheckedForALongTime(amount, temporalUnit, pageable);

        if (page == null) {
            return null;
        }
        return page.map(mapper::getLinkDto);
    }
}