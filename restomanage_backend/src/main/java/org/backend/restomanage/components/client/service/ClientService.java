package org.backend.restomanage.components.client.service;

import org.backend.restomanage.components.client.dto.request.ClientRequestDTO;
import org.backend.restomanage.components.client.dto.response.ClientResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientService {
    ClientResponseDTO createClient(ClientRequestDTO clientRequestDTO);
    ClientResponseDTO getClientById(Long id);
    Page<ClientResponseDTO> getAllClients(Pageable pageable);
    ClientResponseDTO updateClient(Long id, ClientRequestDTO clientRequestDTO);
    void deleteClient(Long id);
    ClientResponseDTO getClientByEmail(String email);
    ClientResponseDTO getClientByPhoneNumber(String phoneNumber);
}
