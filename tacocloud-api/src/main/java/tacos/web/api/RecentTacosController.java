package tacos.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import tacos.Taco;
import tacos.data.TacoRepository;

import java.util.ArrayList;
import java.util.List;

@RepositoryRestController
public class RecentTacosController {

    private final TacoRepository tacoRepo;

    @Autowired
    public RecentTacosController(TacoRepository tacoRepo) {
        this.tacoRepo = tacoRepo;
    }

    @GetMapping(path = "/tacos/recent", produces = "application/hal+json")
    public ResponseEntity<CollectionModel<TacoResource>> recentTacos() {
        List<Taco> tacos = new ArrayList<>();
        tacoRepo.findAll().subscribe(tacos::add);
        CollectionModel<TacoResource> recentResources = new TacoResourceAssembler().toCollectionModel(tacos);

        recentResources.add(WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(RecentTacosController.class).recentTacos()
                )
                .withRel("recents"));
        return new ResponseEntity<>(recentResources, HttpStatus.OK);
    }
}
