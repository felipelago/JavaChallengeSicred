package com.project.challenge.repository;

import com.project.challenge.entity.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    @Query("SELECT tr FROM Transacao tr WHERE tr.descricao.status = 'CANCELADO' AND tr.id = :id")
    Optional<Transacao> findEstornoById(Long id);

    @Query("SELECT tr FROM Transacao tr WHERE tr.descricao.status = 'CANCELADO'")
    List<Transacao> findAllEstorno();

}
