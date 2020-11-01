package com.manoloscorp.livinother.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TB_USER")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

  private static final long serialVersionUID = -1299061206641026380L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String name;

  @Column(nullable = false, length = 100)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private LocalDate birthDate;

  @Column(nullable = false)
  private String genre;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UserType userType;

  @Column(nullable = false)
  private String eatingHabit;

  @OneToOne(cascade = CascadeType.ALL)
  private MedicalHistory medicalHistory;

  public void setBirthDate(String birthDate) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate localDate = LocalDate.parse(birthDate, formatter);
    this.birthDate = localDate;
  }
}
