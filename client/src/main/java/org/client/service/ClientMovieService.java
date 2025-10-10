package org.client.service;

import org.client.service.dto.ClientMovieDto;
import org.client.service.exceptions.ClientMovieNotRemovableException;
import org.client.service.utils.exceptions.InputValidationException;
import org.client.service.utils.exceptions.InstanceNotFoundException;

import java.util.List;



public interface ClientMovieService {

    public Long addMovie(ClientMovieDto movie)
            throws InputValidationException;

    public void updateMovie(ClientMovieDto movie)
            throws InputValidationException, InstanceNotFoundException;

    public void removeMovie(Long movieId) throws InstanceNotFoundException,
            ClientMovieNotRemovableException;

    public List<ClientMovieDto> findMovies(String keywords);

}