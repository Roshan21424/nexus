package com.MallareddyCollegeOfTechnology.app.Entities.ClassRepositoryEntities;

import com.MallareddyCollegeOfTechnology.app.Entities.Section;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;





@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClassRepositoryFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //id of the file
    private String name; //name of the file
    private String type; //type of the file
    private String size; //size of the file
    private String uploadedBy; //uploaded by teacher name
    private String category;//category name
    private String subject; //subject name
    private String url;//url of the file
    private LocalDateTime uploadDate;//uploaded date of the file

    @OneToOne
    @JoinColumn(name="section_id")
    @JsonIgnore           // <-- Hides Section in API response
    private Section section;
}
