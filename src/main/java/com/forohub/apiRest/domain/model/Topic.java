package com.forohub.apiRest.domain.model;


import com.forohub.apiRest.domain.dto.TopicDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
@Entity(name = "Topic")
@Table(name = "topics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotBlank
    String title;
    @NotBlank
    String message;
    @NotNull

    String createDate;
    @NotNull
    int status;
    @NotBlank
    String author;
    @NotBlank
    String course;
    String answers;

    public Topic(TopicDTO dateTopic) {

        this.title = dateTopic.title();
        this.message = dateTopic.message();
        this.createDate = dateTopic.createDate();
        this.status = 1;
        this.author = dateTopic.author();
        this.course = dateTopic.course();
        this.answers = dateTopic.answers();
    }
}
