package com.kchan.project.beyond.notes.domain;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/*
 * Scans for Spring Data repositories within the package of this annotated class
 * ... Seems to work without though. Why Spring?
 */
@EnableJpaRepositories
public class Config {}
