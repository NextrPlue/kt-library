package ktlibrary.domain;

import ktlibrary.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
@RepositoryRestResource(
    collectionResourceRel = "bookShelves",
    path = "bookShelves"
)
public interface BookShelfRepository
    extends PagingAndSortingRepository<BookShelf, Long> {}
