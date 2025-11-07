package com.PetFinder.PetFinder.dto.websocket;

import com.PetFinder.PetFinder.entity.CollarStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollarStatusUpdateMessage {
    private Long petId;
    private CollarStatus status;
    private Byte batteryLevel;
}
