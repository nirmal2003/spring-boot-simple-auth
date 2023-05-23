package com.example.demo.post.comment;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
@Getter
@Setter
public class Comments {

    @Id
    @GeneratedValue
    private Long id;
    private Long postId;
    private Long userId;
    private String comment;

}
