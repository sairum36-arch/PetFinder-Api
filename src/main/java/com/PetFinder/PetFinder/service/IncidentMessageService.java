package com.PetFinder.PetFinder.service;

import com.PetFinder.PetFinder.dto.messages.MessageResponse;
import com.PetFinder.PetFinder.dto.messages.MessageSendRequest;
import com.PetFinder.PetFinder.entity.CredentialEntity;
import com.PetFinder.PetFinder.entity.IncidentEntity;
import com.PetFinder.PetFinder.entity.IncidentMessageEntity;
import com.PetFinder.PetFinder.entity.UserEntity;
import com.PetFinder.PetFinder.exception.EntityNotFoundException;
import com.PetFinder.PetFinder.mapper.IncidentMessageMapper;
import com.PetFinder.PetFinder.repositories.IncidentMessageRepository;
import com.PetFinder.PetFinder.repositories.IncidentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IncidentMessageService {
    private final IncidentRepository incidentRepository;
    private final IncidentMessageRepository messageRepository;
    private final IncidentMessageMapper messageMapper;

    @Transactional
    public MessageResponse sendMessage(Long incidentId, MessageSendRequest request, CredentialEntity currentUser) {
        IncidentEntity incident = findIncidentAndCheckAccess(incidentId, currentUser.getUserEntity());
        IncidentMessageEntity message = new IncidentMessageEntity();
        message.setIncidentEntity(incident);
        message.setSender(currentUser.getUserEntity());
        message.setMessageText(request.getMessageText());
        message.setSentAt(LocalDateTime.now());
        IncidentMessageEntity savedMessage = messageRepository.save(message);
        return messageMapper.toDto(savedMessage);
    }
    @Transactional(readOnly = true)
    public List<MessageResponse> getMessages(Long incidentId, CredentialEntity currentUser) {
        IncidentEntity incident = findIncidentAndCheckAccess(incidentId, currentUser.getUserEntity());
        List<IncidentMessageEntity> messages = messageRepository.findByIncidentEntityOrderBySentAt(incident);
        return messageMapper.toDtoList(messages);
    }

    private IncidentEntity findIncidentAndCheckAccess(Long incidentId, UserEntity user) {
        IncidentEntity incident = incidentRepository.findById(incidentId)
                .orElseThrow(() -> new EntityNotFoundException("Инцидент с ID " + incidentId + " не найден."));
        boolean isOwner = incident.getPetEntity().getUserEntity().getId().equals(user.getId());
        boolean isHelper = incident.getResponses().stream()
                .anyMatch(participation -> participation.getHelperUserEntity().getId().equals(user.getId()));
        if (!isOwner && !isHelper) {
            throw new AccessDeniedException("У вас нет доступа к этому чату.");
        }

        return incident;
    }
}
