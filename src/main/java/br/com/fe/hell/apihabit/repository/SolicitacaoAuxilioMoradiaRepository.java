package br.com.fe.hell.apihabit.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.fe.hell.apihabit.model.SolicitacaoAuxilioMoradia;


public interface SolicitacaoAuxilioMoradiaRepository extends JpaRepository<SolicitacaoAuxilioMoradia, Long> {
	
	void deleteByideDeputado(Integer ideDeputado);

	Optional<SolicitacaoAuxilioMoradia> findByideDeputado(Integer ideDeputado);

	SolicitacaoAuxilioMoradia getReferenceByideDeputado(Integer ideDeputado);

	List<SolicitacaoAuxilioMoradia> findAllByIdeDeputado(Integer ideDeputado);

	Optional<SolicitacaoAuxilioMoradia> findByIdeDeputadoAndIdentidade(Integer ideDeputado, Integer id);

	void deleteByIdentidade(Long id);

	@Query("SELECT s FROM SolicitacaoAuxilioMoradia s WHERE s.ideDeputado = :ideDeputado AND s.identidade = (SELECT MAX(ss.identidade) FROM SolicitacaoAuxilioMoradia ss WHERE ss.ideDeputado = :ideDeputado)")
	Optional<SolicitacaoAuxilioMoradia> carregarUltimoPorIdeDeputado(@Param("ideDeputado") Integer ideDeputado);
	
	@Query("SELECT s FROM SolicitacaoAuxilioMoradia s WHERE s.dataInicioBeneficio is null and s.ideRequerimento is null and s.dataRequerimento >= :dataRequerimento order by s.ideDeputado asc, s.dataRequerimento desc")
	List<SolicitacaoAuxilioMoradia> listarSolicitacoesCriadasAPartirDe(Date dataRequerimento);
}