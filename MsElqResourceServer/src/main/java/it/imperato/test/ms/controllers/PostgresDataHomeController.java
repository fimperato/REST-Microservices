package it.imperato.test.ms.controllers;

import it.imperato.test.ms.model.postgres.AppUser;
import it.imperato.test.ms.repository.postgres.UserRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class PostgresDataHomeController {

  final UserRepository userRepository;

  @Inject
  public PostgresDataHomeController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GetMapping
  public ModelAndView home() {
    return new ModelAndView("index");
  }

  @GetMapping(value = "/hello")
  public ResponseEntity<String> sayHello() {
    return ResponseEntity.ok().body("Hello there !");
  }

  @PostMapping(value = "/user/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<AppUser> create(@PathVariable String username) {
    AppUser appUser = new AppUser();
    appUser.setUsername(username);
    AppUser saved = userRepository.save(appUser);
    return ResponseEntity.ok().body(saved);
  }

  @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<AppUser>> findAll() {
    final List<AppUser> resultList = new ArrayList<>();
    final Iterable<AppUser> all = userRepository.findAll();
    all.forEach(resultList::add);
    return ResponseEntity.ok().body(resultList);
  }

}
