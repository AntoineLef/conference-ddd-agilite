package ca.ulaval.glo4003.ddd;

import ca.ulaval.glo4003.ddd.ws.domain.md.BillingService;
import ca.ulaval.glo4003.ddd.ws.domain.md.DoctorRepository;
import ca.ulaval.glo4003.ddd.ws.domain.md.ProcedureRepository;
import ca.ulaval.glo4003.ddd.ws.infrastructure.md.InMemoryDoctorRepository;
import ca.ulaval.glo4003.ddd.ws.infrastructure.md.InMemoryProcedureRepository;

/**
 * RESTApi setup without using DI or spring
 */
@SuppressWarnings("all")
public class DoctorBillingMain {
  public static boolean isDev = true; // Would be a JVM argument or in a .property file

  public static void main(String[] args) throws Exception {

    // Setup resources (API)
    BillingService billingService = createBillingService();

  }

  private static BillingService createBillingService() {
    // Setup resources' dependencies (DOMAIN + INFRASTRUCTURE)
    DoctorRepository doctorRepository = new InMemoryDoctorRepository();
    ProcedureRepository procedureRepository = new InMemoryProcedureRepository();

    BillingService contactService = new BillingService(doctorRepository, procedureRepository);

    return contactService;
  }

}
