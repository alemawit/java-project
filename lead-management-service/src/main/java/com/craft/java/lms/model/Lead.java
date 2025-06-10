package com.craft.java.lms.model;

import com.craft.java.lms.converter.JsonMapConverter;
import jakarta.persistence.*;
import lombok.*;

import java.util.Map;

@Entity
@Table(name = "`lead`")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String company;
    private String source;

    @Convert(converter = JsonMapConverter.class)
    @Column(columnDefinition = "json")
    private Map<String, Object> customFields;

    // ✅ Add this field
    private boolean converted;
}
