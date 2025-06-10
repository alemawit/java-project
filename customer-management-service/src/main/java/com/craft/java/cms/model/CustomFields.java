package com.craft.java.cms.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//CustomFields.java
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomFields {
private String birthday;
private String company;
}