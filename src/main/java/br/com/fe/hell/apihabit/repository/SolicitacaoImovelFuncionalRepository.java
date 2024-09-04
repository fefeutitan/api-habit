package br.com.fe.hell.apihabit.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.fe.hell.apihabit.model.SolicitacaoImovelFuncional;

public interface SolicitacaoImovelFuncionalRepository extends JpaRepository<SolicitacaoImovelFuncional, Long> {

	void deleteByIdentidade(Long id);

	Optional<SolicitacaoImovelFuncional> findByIdentidade(Long id);

//	SolicitacaoOcupacao getReferenceByIdentidade(Long idedeputado);

	List<SolicitacaoImovelFuncional> findAllByIdeDeputado(Integer ideDeputado);

	Optional<SolicitacaoImovelFuncional> findByIdeDeputadoAndIdentidade(Integer ideDeputado, Integer id);

	@Query("SELECT s FROM SolicitacaoImovelFuncional s WHERE s.ideDeputado = :ideDeputado AND s.identidade = (SELECT MAX(ss.identidade) FROM SolicitacaoImovelFuncional ss WHERE ss.ideDeputado = :ideDeputado)")
	Optional<SolicitacaoImovelFuncional> carregarUltimoPorIdeDeputado(Integer ideDeputado);
	
	@Query("SELECT s FROM SolicitacaoImovelFuncional s WHERE s.contato is null and s.telefone is null and s.ideImovel is null and s.previsaoOcupacao is null and s.dataRequerimento >= :dataRequerimento order by s.ideDeputado asc, s.dataRequerimento desc")
	List<SolicitacaoImovelFuncional> listarSolicitacoesCriadasAPartirDe(Date dataRequerimento);
	
}