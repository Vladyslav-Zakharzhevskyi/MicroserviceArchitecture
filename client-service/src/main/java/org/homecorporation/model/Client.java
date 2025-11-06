package org.homecorporation.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "Clients")
public record Client(@Id Long id, @Column String email) { }
