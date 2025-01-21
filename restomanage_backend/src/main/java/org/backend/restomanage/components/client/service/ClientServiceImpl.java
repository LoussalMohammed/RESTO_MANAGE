package org.backend.restomanage.components.client.service;

import lombok.RequiredArgsConstructor;
import org.backend.restomanage.components.client.dto.request.ClientRequestDTO;
import org.backend.restomanage.components.client.dto.response.ClientResponseDTO;
import org.backend.restomanage.components.client.mapper.ClientMapper;
import org.backend.restomanage.components.client.repository.ClientRepository;
import org.backend.restomanage.entities.Client;
import org.backend.restomanage.exception.DuplicateResourceException;
import org.backend.restomanage.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public ClientResponseDTO createClient(ClientRequestDTO clientRequestDTO) {
        // Check for duplicate email
        if (clientRepository.existsByEmail(clientRequestDTO.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }
        
        // Check for duplicate phone number
        if (clientRepository.existsByPhoneNumber(clientRequestDTO.getPhoneNumber())) {
            throw new DuplicateResourceException("Phone number already exists");
        }

        Client client = clientMapper.toEntity(clientRequestDTO);
        client = clientRepository.save(client);
        return clientMapper.toDTO(client);
    }

    @Override
    @Transactional(readOnly = true)
    public ClientResponseDTO getClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
        return clientMapper.toDTO(client);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClientResponseDTO> getAllClients(Pageable pageable) {
        return clientRepository.findAll(pageable)
                .map(clientMapper::toDTO);
    }

    @Override
    public ClientResponseDTO updateClient(Long id, ClientRequestDTO clientRequestDTO) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));

        // Check for duplicate email if changed
        if (!client.getEmail().equals(clientRequestDTO.getEmail()) &&
            clientRepository.existsByEmail(clientRequestDTO.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        // Check for duplicate phone number if changed
        if (!client.getPhoneNumber().equals(clientRequestDTO.getPhoneNumber()) &&
            clientRepository.existsByPhoneNumber(clientRequestDTO.getPhoneNumber())) {
            throw new DuplicateResourceException("Phone number already exists");
        }

        clientMapper.updateEntityFromDTO(clientRequestDTO, client);
        client.setUpdatedAt(LocalDateTime.now());
        client = clientRepository.save(client);
        return clientMapper.toDTO(client);
    }

    @Override
    public void deleteClient(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Client not found with id: " + id);
        }
        clientRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ClientResponseDTO getClientByEmail(String email) {
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with email: " + email));
        return clientMapper.toDTO(client);
    }

    @Override
    @Transactional(readOnly = true)
    public ClientResponseDTO getClientByPhoneNumber(String phoneNumber) {
        Client client = clientRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with phone number: " + phoneNumber));
        return clientMapper.toDTO(client);
    }
}
