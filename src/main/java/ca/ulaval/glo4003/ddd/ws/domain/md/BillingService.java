package ca.ulaval.glo4003.ddd.ws.domain.md;

import java.time.LocalDate;

public class BillingService {

  private DoctorRepository doctorRepository;
  private HospitalPrimeRateFetcher rateFetcher;

  public BillingService(DoctorRepository doctorRepository,
                        ProcedureRepository procedureRepository)
  {
    this.doctorRepository = doctorRepository;

  }

  public void addNewProcedure(String doctorId, ProcedureInfo procedureInfo) {
    Doctor doctor = doctorRepository.findById(doctorId);

    doctor.addProcedure(new Procedure(procedureInfo.hospitalName,
                                      procedureInfo.startTime,
                                      procedureInfo.endTime));

    doctorRepository.save(doctor);
  }

  public void addDoctor(Doctor docter) {
    doctorRepository.save(docter);
  }

  public double dailyTotalOf(String doctorId, LocalDate wantedDate) {
    Doctor doctor = doctorRepository.findById(doctorId);
    return doctor.calculateDailyWage(wantedDate, rateFetcher);
  }

}
