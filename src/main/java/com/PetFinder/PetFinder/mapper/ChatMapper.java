package com.PetFinder.PetFinder.mapper;

import com.PetFinder.PetFinder.dto.chat.ChatListItemResponse;
import com.PetFinder.PetFinder.entity.IncidentEntity;
import com.PetFinder.PetFinder.entity.IncidentMessageEntity;
import com.PetFinder.PetFinder.service.FileStoragePackage.UrlBuilderService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UrlBuilderService.class})
public interface ChatMapper {
    @Mapping(source = "incident.id", target = "incidentId")
    @Mapping(source = "incident.petEntity.name", target = "petName")
    @Mapping(source = "incident.petEntity", target = "petMainPhotoUrl") // MapStruct вызовет метод из UrlBuilderService
    @Mapping(source = "lastMessage.messageText", target = "lastMessageText")
    @Mapping(source = "lastMessage.sentAt", target = "lastMessageSentAt")
    @Mapping(source = "lastMessage.sender.id", target = "lastMessageSenderId")
    @Mapping(target = "unreadMessagesCount", constant = "0") // Пока ставим заглушку
    ChatListItemResponse toChatListItem(IncidentEntity incident, IncidentMessageEntity lastMessage);
}
