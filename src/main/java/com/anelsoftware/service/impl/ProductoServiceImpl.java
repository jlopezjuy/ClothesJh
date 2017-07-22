package com.anelsoftware.service.impl;

import com.anelsoftware.service.ProductoService;
import com.anelsoftware.domain.Producto;
import com.anelsoftware.repository.ProductoRepository;
import com.anelsoftware.repository.search.ProductoSearchRepository;
import com.anelsoftware.service.dto.ProductoDTO;
import com.anelsoftware.service.mapper.ProductoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Producto.
 */
@Service
@Transactional
public class ProductoServiceImpl implements ProductoService{

    private final Logger log = LoggerFactory.getLogger(ProductoServiceImpl.class);

    private final ProductoRepository productoRepository;

    private final ProductoMapper productoMapper;

    private final ProductoSearchRepository productoSearchRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository, ProductoMapper productoMapper, ProductoSearchRepository productoSearchRepository) {
        this.productoRepository = productoRepository;
        this.productoMapper = productoMapper;
        this.productoSearchRepository = productoSearchRepository;
    }

    /**
     * Save a producto.
     *
     * @param productoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProductoDTO save(ProductoDTO productoDTO) {
        log.debug("Request to save Producto : {}", productoDTO);
        Producto producto = productoMapper.toEntity(productoDTO);
        producto = productoRepository.save(producto);
        ProductoDTO result = productoMapper.toDto(producto);
        productoSearchRepository.save(producto);
        return result;
    }

    /**
     *  Get all the productos.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Productos");
        return productoRepository.findAll(pageable)
            .map(productoMapper::toDto);
    }

    /**
     *  Get one producto by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ProductoDTO findOne(Long id) {
        log.debug("Request to get Producto : {}", id);
        Producto producto = productoRepository.findOne(id);
        return productoMapper.toDto(producto);
    }

    /**
     *  Delete the  producto by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Producto : {}", id);
        productoRepository.delete(id);
        productoSearchRepository.delete(id);
    }

    /**
     * Search for the producto corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProductoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Productos for query {}", query);
        Page<Producto> result = productoSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(productoMapper::toDto);
    }
}
