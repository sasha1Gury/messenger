package org.example.backend.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class FriendId implements Serializable {
    private Long userId;
    private Long friendId;
}
