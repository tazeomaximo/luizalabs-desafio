package br.com.luizalabs.desafio.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.luizalabs.desafio.dto.Paginacao;
import br.com.luizalabs.desafio.dto.ProdutoDto;
import br.com.luizalabs.desafio.dto.ProdutoPaginacaoDto;
import br.com.luizalabs.desafio.entity.ProdutoFavoritoEntity;
import br.com.luizalabs.desafio.entity.ProdutoFavoritoId;
import br.com.luizalabs.desafio.integration.ProdutoIntegration;
import br.com.luizalabs.desafio.repository.ProdutoFavoritoRepository;

@Service
public class ProdutoFavoritoService {

	
	private ProdutoFavoritoRepository favoritoRepository;
	private ProdutoIntegration produtoIntegration;
	
	public ProdutoFavoritoService(ProdutoFavoritoRepository favoritoRepository, ProdutoIntegration produtoIntegration) {
		this.favoritoRepository = favoritoRepository;
		this.produtoIntegration = produtoIntegration;
	}
	
	public ProdutoPaginacaoDto getProdutoFavorito(Long idCliente, Integer page, Integer size){
		
		ProdutoPaginacaoDto paginacaoDto = new ProdutoPaginacaoDto(new Paginacao(page, size));
		
		if(page > 0) {
			page--;
		}
		
		//TODO colocar validação quando o cliente informar page = 0 ou uma paginacao que não existe
		
		PageRequest pr = PageRequest.of(page, size);
		
		PageImpl<ProdutoFavoritoEntity> produtos = favoritoRepository.findByIdIdCliente(idCliente, pr);
		
		
		List<ProdutoDto> produtoDtos = new ArrayList<ProdutoDto>();
		
		Consumer<ProdutoFavoritoEntity> consumer = s -> {
			ProdutoDto dto = produtoIntegration.getProdutoById(s.getId().getIdProduto());
			
			produtoDtos.add(dto);
		};
		
		
		
		produtos.getContent().forEach(consumer);
		
		paginacaoDto.setProdutos(produtoDtos);
		
		return paginacaoDto;
	}
	
	public void removerProdutoFavorito(Long idCliente, Set<String> produtos) {
		favoritoRepository.deleteByIdClienteAndIdProduto(idCliente, produtos);
	}
	
	public void adicionarProdutoFavorito(Long idCliente, Set<String> idProdutos) {
		
		Set<ProdutoFavoritoEntity> produtos = new HashSet<ProdutoFavoritoEntity>();
		
		Consumer<String> consumer = s ->{
			ProdutoFavoritoEntity entity = new ProdutoFavoritoEntity();
			
			ProdutoFavoritoId id = new ProdutoFavoritoId();
			
			id.setIdCliente(idCliente);
			id.setIdProduto(s);
			
			entity.setId(id);
			
			produtos.add(entity);
			
		};
		
		idProdutos.parallelStream().forEach(consumer);
		
		
		favoritoRepository.saveAll(produtos);
	}


	
	
}
