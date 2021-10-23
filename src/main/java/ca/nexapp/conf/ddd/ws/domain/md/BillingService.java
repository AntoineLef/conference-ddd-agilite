package ca.nexapp.conf.ddd.ws.domain.md;

import java.time.LocalDate;

public class BillingService {

  private DoctorRepository doctorRepository;

  public BillingService(DoctorRepository doctorRepository) {
    this.doctorRepository = doctorRepository;
  }

  public void addNewProcedure(String doctorId, ProcedureInfo procedureInfo) throws DoctorNotFoundException {
    String procedureId = ProcedureIdGenerator.generateId();
    Procedure procedure = new Procedure(procedureId,
                                        doctorId,
                                        procedureInfo.hospitalName,
                                        procedureInfo.startTime,
                                        procedureInfo.endTime);

    Doctor doctor = doctorRepository.findById(doctorId);
    doctor.addProcedure(procedure);

    doctorRepository.update(doctor);
  }

  public void addDoctor(Doctor docter) {
    doctorRepository.save(docter);
  }

  public double dailyTotalOf(String doctorId, LocalDate wantedDate) {

    Doctor doctor = doctorRepository.findById(doctorId);

    return doctor.calculateDailyBillable(wantedDate);
  }

}
