package br.com.luizalabs.desafio.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.luizalabs.desafio.domain.MensagemEnum;
import br.com.luizalabs.desafio.dto.PaginacaoDto;
import br.com.luizalabs.desafio.dto.ProdutoDto;
import br.com.luizalabs.desafio.dto.ProdutoPaginacaoDto;
import br.com.luizalabs.desafio.entity.ProdutoFavoritoEntity;
import br.com.luizalabs.desafio.entity.ProdutoFavoritoId;
import br.com.luizalabs.desafio.exception.BadRequestException;
import br.com.luizalabs.desafio.integration.ProdutoIntegration;
import br.com.luizalabs.desafio.repository.ClienteRepository;
import br.com.luizalabs.desafio.repository.ProdutoFavoritoRepository;
import br.com.luizalabs.desafio.util.MensagemUtil;

@Service
public class ProdutoFavoritoService extends AbstractService {

	private static final String IDS = "ids";

	private static final Logger LOG = LoggerFactory.getLogger(ProdutoFavoritoService.class);

	private ProdutoFavoritoRepository favoritoRepository;
	private ProdutoIntegration produtoIntegration;

	private ClienteRepository clienteRepository;

	public ProdutoFavoritoService(ProdutoFavoritoRepository favoritoRepository, ProdutoIntegration produtoIntegration,
			MensagemUtil mensagemUtil, ClienteRepository clienteRepository) {
		super(mensagemUtil);
		this.favoritoRepository = favoritoRepository;
		this.produtoIntegration = produtoIntegration;
		this.clienteRepository = clienteRepository;
	}

	public ProdutoPaginacaoDto getProdutoFavorito(Long idCliente, Integer page, Integer size) {

		clienteRepository.isIdClienteExiste(idCliente).orElseThrow(() -> registroNaoEncontrado(idCliente));

		validarPaginacao(page, size);

		PageRequest pr = PageRequest.of(page - 1, size);

		LOG.debug("Iniciando a busca dos registros no banco.");
		PageImpl<ProdutoFavoritoEntity> produtos = favoritoRepository.findByIdIdCliente(idCliente, pr);
		LOG.debug("Iniciando a busca dos detalhes dos registros no servico");

		List<ProdutoDto> produtoDtos = new ArrayList<ProdutoDto>();

		Consumer<ProdutoFavoritoEntity> consumer = s -> {
			ProdutoDto dto = produtoIntegration.getProdutoById(s.getId().getIdProduto());

			produtoDtos.add(dto);
		};

		produtos.getContent().forEach(consumer);

		PaginacaoDto p = iniciaPaginacao(page, size, produtos);

		ProdutoPaginacaoDto paginacaoDto = new ProdutoPaginacaoDto(p);

		paginacaoDto.setProdutos(produtoDtos);

		return paginacaoDto;
	}

	public void removerProdutoFavorito(Long idCliente, Set<String> produtos) {
		validarProdutos(produtos);

		clienteRepository.isIdClienteExiste(idCliente).orElseThrow(() -> registroNaoEncontrado(idCliente));

		favoritoRepository.deleteByIdClienteAndIdProduto(idCliente, produtos);
	}

	public void adicionarProdutoFavorito(Long idCliente, Set<String> idProdutos) {

		Set<ProdutoFavoritoEntity> produtos = new HashSet<ProdutoFavoritoEntity>();

		validarProdutos(idProdutos);

		Consumer<String> consumer = s -> {

			ProdutoDto produto = null;

			if (StringUtils.hasText(s))
				produto = produtoIntegration.getProdutoById(s.trim());

			if (produto == null || produto.getId() == null)
				throw registroNaoEncontrado(s);

			ProdutoFavoritoEntity entity = new ProdutoFavoritoEntity();

			ProdutoFavoritoId id = new ProdutoFavoritoId();

			id.setIdCliente(idCliente);
			id.setIdProduto(s);

			entity.setId(id);

			produtos.add(entity);

		};

		idProdutos.forEach(consumer);

		try {
			LOG.debug("Inciar salvar todos registros");
			favoritoRepository.saveAll(produtos);
			LOG.debug("Finalizado salvar todos registros");
		} catch (DataIntegrityViolationException e) {
			LOG.error("Id Cliente {} nao existe.", idCliente);
			throw registroNaoEncontrado(idCliente);
		}
	}

	private void validarProdutos(Set<String> idProdutos) {
		if (idProdutos == null || idProdutos.isEmpty()) {
			throw new BadRequestException(getMensagemDto(MensagemEnum.ERRO_CAMPO_OBRIGATORIO, IDS));
		}
	}

	public ProdutoPaginacaoDto getAllProdutos(Integer page) {
		
		if(page == null || page < 1)
			throw new BadRequestException(getMensagemDto(MensagemEnum.ERRO_PAGINA_NAO_ENCONTRADA, page));

		ProdutoPaginacaoDto produtoPaginacaoDto = produtoIntegration.getAll(page);

		if (produtoPaginacaoDto == null || produtoPaginacaoDto.getProdutos() == null
				|| produtoPaginacaoDto.getProdutos().isEmpty())
			throw new BadRequestException(getMensagemDto(MensagemEnum.ERRO_PAGINA_NAO_ENCONTRADA, page));

		return produtoPaginacaoDto;
	}

}
