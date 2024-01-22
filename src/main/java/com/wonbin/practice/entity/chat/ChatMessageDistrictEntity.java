package com.wonbin.practice.entity.chat;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "district_message")
public class ChatDistrictEntity {
}
