package br.com.dbv.financeiro.service;

import br.com.dbv.financeiro.dto.ErrorDTO;
import br.com.dbv.financeiro.dto.UserDTO;
import br.com.dbv.financeiro.enums.UserTypeEnum;
import br.com.dbv.financeiro.exception.CustomException;
import br.com.dbv.financeiro.model.Club;
import br.com.dbv.financeiro.model.Pathfinder;
import br.com.dbv.financeiro.model.Unit;
import br.com.dbv.financeiro.repository.ClubRepository;
import br.com.dbv.financeiro.repository.UnitRepository;
import br.com.dbv.financeiro.repository.UserRepository;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private ClubRepository clubRepository;

    public UserDTO createUser(UserDTO request) throws CustomException {

        Optional<Unit> unit;
        Pathfinder user;

        var club = clubRepository.findById(request.getClubId());

        if (!club.isPresent()) {
            throw new CustomException(new ErrorDTO("400", "Club not found", "Club not found in database"));
        }

        var oldUser = repository.findByName(request.getName());

        if (oldUser.isPresent()) {
            throw new CustomException(new ErrorDTO("400", "User already exists", "User already exists"));
        }

        if (request.getUnitId() != null) {
            unit = unitRepository.findById(request.getUnitId());

            if (!unit.isPresent()) {
                throw new CustomException(new ErrorDTO("400", "Unit not found", "Unit not found in database"));
            }

            user = this.convertDtoToUser(request, club.get(), unit.get());
        } else {
            user = this.convertDtoToUser(request, club.get(), null);
        }

        if (user.getBirthDate() == null) {
            user.setBirthDate(new Date());
        }

        var userDTO = convertUserToDTO(repository.save(user));

        return userDTO;

    }

    public UserDTO updateUser(UUID userId, UserDTO request) throws CustomException {

        var user = repository.findById(userId);

        if (!user.isPresent()) {
            throw new CustomException(new ErrorDTO("400", "User not found", "User not found"));
        }

        /** Name */
        if (!StringUtils.isBlank(request.getName()) && !request.getName().equals(user.get().getName())) {

            var userExists = repository.findByName(request.getName());

            if (userExists.isPresent()) {
                throw new CustomException(new ErrorDTO("400", "User name already exists", "User name already exists"));
            }

            user.get().setName(request.getName());
        }

        /** User Type */
        if (request.getUserType() != null) {
            user.get().setUserType(request.getUserType());
        }

        /** Birthdate */
        if (request.getBirthDate() != null) {
            user.get().setBirthDate(request.getBirthDate());
        }

        /** Gender */
        if (request.getGender() != null) {
            user.get().setGender(request.getGender());
        }

        /** Active */
        if (request.getActive() != null) {
            user.get().setActive(request.getActive());
        }

        /** Unit */
        if (request.getUnitId() != null) {
            var unit = unitRepository.findById(request.getUnitId());

            if (!unit.isPresent()) {
                throw new CustomException(new ErrorDTO("400", "Unit not found", "Unit not found in database"));
            }

            user.get().setUnit(unit.get());
        }

        repository.save(user.get());

        var userDTO = convertUserToDTO(user.get());

        return userDTO;

    }

    public UserDTO getUsersById(UUID id) throws CustomException {

        var user = repository.findById(id);

        if (!user.isPresent()) {
            throw new CustomException(new ErrorDTO("400", "User not found", "User not found in database"));
        }

        return convertUserToDTO(user.get());
    }

    public ArrayList<UserDTO> getUsersByClubWithEventualUsers(Long id, Boolean eventualUser, Boolean onlyActives) {

        var userOptionalList = new ArrayList<Pathfinder>();

        if (eventualUser != null && eventualUser) {
            if (onlyActives == null || onlyActives)
                userOptionalList = repository.findByClubIdAndActiveOrderByName(id, Boolean.TRUE);
            else
                userOptionalList = repository.findByClubIdOrderByName(id);
        } else {
            if (onlyActives == null || onlyActives)
                userOptionalList = repository.findByClubIdAndActiveAndUserTypeNotOrderByName(id, Boolean.TRUE, UserTypeEnum.EVENTUAL);
            else
                userOptionalList = repository.findByClubIdAndUserTypeNotOrderByName(id, UserTypeEnum.EVENTUAL);
        }

        ArrayList<UserDTO> userList = new ArrayList<>();

        userOptionalList.forEach(user -> userList.add(convertUserToDTO(user)));

        return userList;
    }

    public ArrayList<?> getUserByUnitId(Long id) {

        var userOptionalList = repository.findByUnitIdAndActive(id, Boolean.TRUE);

        ArrayList<UserDTO> userList = new ArrayList<>();

        userOptionalList.forEach(user -> userList.add(convertUserToDTO(user.get())));

        return userList;
    }

    public void activeOrInactiveUser(UUID userId) throws CustomException {

        var user = repository.findById(userId);

        if (!user.isPresent()) {
            throw new CustomException(new ErrorDTO("400", "User not found", "User not found in database"));
        }

        user.get().setActive(!user.get().getActive());

        repository.save(user.get());

    }


    private UserDTO convertUserToDTO(Pathfinder user) {

        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .birthDate(user.getBirthDate())
                .userType(user.getUserType())
                .bank(user.getBank())
                .clubId(user.getClub().getId())
                .unitId(user.getUnit() != null ? user.getUnit().getId() : null)
                .gender(user.getGender())
                .active(user.getActive())
                .build();

    }

    private Pathfinder convertDtoToUser(UserDTO user, Club club, Unit unit) {

        return Pathfinder.builder()
                .name(user.getName())
                .birthDate(user.getBirthDate())
                .userType(user.getUserType())
                .bank(0.0)
                .club(club)
                .unit(unit)
                .gender(user.getGender())
                .active(user.getActive() != null ? user.getActive() : Boolean.TRUE)
                .build();

    }

}
