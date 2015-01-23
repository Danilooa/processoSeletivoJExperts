package br.com.jexperts.danilooa.crudpessoas.service;

import java.util.List;

import br.com.jexperts.danilooa.crudpessoas.dto.FiltroListagemPessoasDTO;
import br.com.jexperts.danilooa.crudpessoas.entity.Pessoa;
import br.com.jexperts.danilooa.crudpessoas.exception.NegocioException;


public interface PessoaService {

    void inserir(Pessoa pessoa) throws NegocioException;
    Boolean possuiFilhos(Long idPessoa);
    void excluir(Pessoa pessoa);
    List<Pessoa> listarPessoas(FiltroListagemPessoasDTO filtroListagemPessoasDTO);
    Long contaTotalPessoas(FiltroListagemPessoasDTO filtroListagemPessoasDTO);
    Pessoa getPessoa(Long idPessoa);

}
