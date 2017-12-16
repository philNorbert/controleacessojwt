package br.edu.cesar.philippe.accesscontrol.service;

import br.edu.cesar.philippe.accesscontrol.model.User;
import br.edu.cesar.philippe.accesscontrol.request.UserDTO;

import java.util.List;

public interface IFeatureService {
    User findOne(String username);
    List<User> findAll(String queryBusca);
    User save(final UserDTO request);
}
