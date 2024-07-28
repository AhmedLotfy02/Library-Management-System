package com.LibraryManagementSystem.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@ToString(exclude = "borrowingRecords")
public class Patron {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    private String contactInformation;

    @OneToMany(mappedBy = "patron", cascade = CascadeType.ALL)
    @JsonIgnore

    private List<BorrowingRecord> borrowingRecords;

}