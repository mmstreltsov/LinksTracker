package scrapper.model.impl;

import org.springframework.data.domain.Page;
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

@Service
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
        validateLink(linkDTO);
        linkRepository.add(mapper.getLink(linkDTO));
    }

    private void validateLink(LinkDTO linkDTO) {
        Chat chat = chatRepository.findById(linkDTO.getChat().getChatId());
        if (chat == null) {
            throw new ClientException(HttpStatus.BAD_REQUEST.value(), "Not registered yet");
        }
        if (chat.getLinks().size() >= 1024) {
            throw new ClientException(HttpStatus.BAD_REQUEST.value(), "Too many links. Available links per 1024");
        }
        if (linkDTO.getUrl().isEmpty()) {
            throw new ClientException(HttpStatus.BAD_REQUEST.value(), "Empty link URL");
        }
        if (linkDTO.getUrl().length() > 512) {
            throw new ClientException(HttpStatus.BAD_REQUEST.value(), "URL too long. Max 255 characters");
        }
    }

    @Override
    public void removeLink(LinkDTO linkDTO) {
        Link link = linkRepository.findByUlrAndChatId(linkDTO.getUrl(), linkDTO.getChat().getChatId());
        linkRepository.remove(link);
    }

    @Override
    public void setCheckFieldToNow(LinkDTO linkDTO) {
        Link link = linkRepository.findByUlrAndChatId(linkDTO.getUrl(), linkDTO.getChat().getChatId());
        link.setCheckedAt(OffsetDateTime.now());
        linkRepository.update(link);
    }

    @Override
    public void setUpdateFieldToNow(LinkDTO linkDTO) {
        Link link = linkRepository.findByUlrAndChatId(linkDTO.getUrl(), linkDTO.getChat().getChatId());
        link.setUpdatedAt(OffsetDateTime.now());
        linkRepository.update(link);
    }

    @Override
    public Page<LinkDTO> findUniqueUrlWhatNotCheckedForALongTime(int amount, TemporalUnit temporalUnit, Pageable pageable) {
        Page<Link> page = linkRepository.findUniqueUrlWhatNotCheckedForALongTime(amount, temporalUnit, pageable);
        return page.map(mapper::getLinkDto);
    }
}