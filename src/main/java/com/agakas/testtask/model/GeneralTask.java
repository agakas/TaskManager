package com.agakas.testtask.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
//Модель с краткими данными о задаче
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeneralTask {

    Long id;
    String title;
    String status;

}
