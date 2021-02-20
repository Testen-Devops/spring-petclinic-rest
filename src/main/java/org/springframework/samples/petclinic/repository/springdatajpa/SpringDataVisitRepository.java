/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
/**
 * Spring Data JPA specialization of the {@link VisitRepository} interface
 *
 * @author Michael Isvy
 * @author Vitaliy Fedoriv
 */

@Profile("spring-data-jpa")
public interface SpringDataVisitRepository extends VisitRepository, Repository<Visit, Integer>, VisitRepositoryOverride {

    @Override
    @Query("SELECT v FROM Visit v WHERE v.description LIKE %:key% OR v.date LIKE %:key% OR v.pet.id LIKE %:key% OR v.pet.name LIKE %:key% OR v.pet.owner.firstName LIKE %:key% OR v.pet.owner.lastName LIKE %:key%  OR v.vet.id LIKE %:key%")
    Collection<Visit> findByKey(@Param("key") String key);
}
