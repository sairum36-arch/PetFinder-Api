package com.PetFinder.PetFinder.service;

import com.PetFinder.PetFinder.dto.chat.ChatListItemResponse;
import com.PetFinder.PetFinder.entity.CredentialEntity;
import com.PetFinder.PetFinder.entity.IncidentEntity;
import com.PetFinder.PetFinder.entity.IncidentMessageEntity;
import com.PetFinder.PetFinder.mapper.ChatMapper;
import com.PetFinder.PetFinder.repositories.IncidentMessageRepository;
import com.PetFinder.PetFinder.repositories.IncidentParticipationRepository;
import com.PetFinder.PetFinder.repositories.IncidentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final IncidentRepository incidentRepository;
    private final IncidentParticipationRepository participationRepository;
    private final IncidentMessageRepository messageRepository;
    private final ChatMapper chatMapper;

    @Transactional(readOnly = true)
    public List<ChatListItemResponse> getChatListForUser(CredentialEntity currentUser) {

        List<IncidentEntity> ownerIncidents = incidentRepository.findAllByPetOwner(currentUser.getUserEntity());
        List<IncidentEntity> helperIncidents = participationRepository.findAllIncidentsByHelper(currentUser.getUserEntity());
        List<IncidentEntity> allUserIncidents = Stream.concat(ownerIncidents.stream(), helperIncidents.stream()).distinct().collect(Collectors.toList());
        List<ChatListItemResponse> chatList = new ArrayList<>();
        for (IncidentEntity incident : allUserIncidents) {
            IncidentMessageEntity lastMessage = messageRepository.findLastMessageForIncident(incident).orElse(null);
            chatList.add(chatMapper.toChatListItem(incident, lastMessage));
        }

        return chatList;
    }
}

