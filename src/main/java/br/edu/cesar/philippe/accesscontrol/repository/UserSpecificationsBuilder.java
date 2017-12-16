package br.edu.cesar.philippe.accesscontrol.repository;

import br.edu.cesar.philippe.accesscontrol.model.User;
import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import java.util.ArrayList;
import java.util.List;

@Getter
public class UserSpecificationsBuilder {
     
    private final List<SearchCriteria> params = new ArrayList<>();
 
    public UserSpecificationsBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }
 
    public Specification<User> build() {
        if (params.size() == 0) {
            return null;
        }
 
        List<Specification<User>> specs = new ArrayList<Specification<User>>();
        for (SearchCriteria param : params) {
            specs.add(new UserSpecification(param));
        }
 
        Specification<User> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = Specifications.where(result).and(specs.get(i));
        }
        return result;
    }
}