package br.com.jexperts.danilooa.crudpessoas.jsf.datamodels;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.jexperts.danilooa.crudpessoas.dto.FiltroListagemPessoasDTO;
import br.com.jexperts.danilooa.crudpessoas.entity.Pessoa;
import br.com.jexperts.danilooa.crudpessoas.service.PessoaService;

public class PessoaLazyDataModel extends LazyDataModel<Pessoa> {

    private static final long serialVersionUID = 1L;

    private PessoaService pessoaService;
    private FiltroListagemPessoasDTO filtroListagemPessoasDTO;
    private List<Pessoa> pessoasDaPaginaCorrente;

    public PessoaLazyDataModel(PessoaService pessoaService, FiltroListagemPessoasDTO filtroListagemPessoasDTO) {
	this.pessoaService = pessoaService;
	this.filtroListagemPessoasDTO = filtroListagemPessoasDTO;
    }

    @Override
    public Pessoa getRowData(String idPessoa) {
	return pessoaService.getPessoa(Long.valueOf(idPessoa));
    }

    @Override
    public Object getRowKey(Pessoa pessoa) {
	return pessoa.getId();
    }

    @Override
    public List<Pessoa> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
	filtroListagemPessoasDTO.setIndexDoPrimeiraRegistroDaPagina(first);
	filtroListagemPessoasDTO.setQuantidadeDeRegistrosPorPagina(pageSize);
	List<Pessoa> listaPessoas = pessoaService.listarPessoas(filtroListagemPessoasDTO);
	this.pessoasDaPaginaCorrente = listaPessoas;
	int dataSize = Integer.valueOf("" + pessoaService.contaTotalPessoas(filtroListagemPessoasDTO));
	this.setRowCount(dataSize);
	return listaPessoas;
    }

    public List<Pessoa> getPessoasDaPaginaCorrente() {
        return pessoasDaPaginaCorrente;
    }
    
    
}
