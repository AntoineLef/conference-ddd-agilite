package ca.nexapp.conf.ddd.ws.domain.contact;

import java.util.List;

public interface ContactRepository {
  List<Contact> findAll();

  Contact findById(String id);

  void update(Contact contact)
          throws ContactNotFoundException;

  void save(Contact contact);

  void remove(String id);
}
