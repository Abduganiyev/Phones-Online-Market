package services.implement;

import dto.Response;
import enums.Role;
import repository.RoleRepository;
import repository.implement.RoleRepositoryImp;
import services.RoleService;

import java.sql.SQLException;
import java.util.List;

public class RoleServiceImp implements RoleService {
    public static RoleRepository roleRepository = new RoleRepositoryImp();
    @Override
    public Response<Role> saveAll(List<Role> roles) throws SQLException {
        Response<Role> response = roleRepository.saveAll(roles);
        return response;
    }
}
