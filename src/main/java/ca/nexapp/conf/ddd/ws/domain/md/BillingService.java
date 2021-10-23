package ca.nexapp.conf.ddd.ws.domain.md;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

public class BillingService {

  private static final double DAILY_WORKED_HOURS = 8.0;

  private DoctorRepository doctorRepository;
  private ProcedureRepository procedureRepository;

  public BillingService(DoctorRepository doctorRepository, ProcedureRepository procedureRepository) {
    this.doctorRepository = doctorRepository;
    this.procedureRepository = procedureRepository;
  }

  public void addNewProcedure(String doctorId, ProcedureInfo procedureInfo) {
    String procedureId = ProcedureIdGenerator.generateId();
    procedureRepository.add(new Procedure(procedureId,
                                          doctorId,
                                          procedureInfo.hospitalName,
                                          procedureInfo.startTime,
                                          procedureInfo.endTime));
  }

  public void addDoctor(Doctor docter) {
    doctorRepository.save(docter);
  }

  public double dailyTotalOf(String doctorId, LocalDate wantedDate) {
    BilledAmount totalBilled = new BilledAmount();

    Doctor doctor = doctorRepository.findById(doctorId);

    List<Procedure> procedures = doctor.getProcedures();
    for (Procedure procedure : procedures) {

      if (procedure.isSameDate(wantedDate)) {
        Duration procedureDuration = procedure.duration();

        double hourWorkedRatio = procedureDuration.toHours() / DAILY_WORKED_HOURS;
        double dailyRate = 600.0;

        totalBilled = totalBilled.addBillable(dailyRate, hourWorkedRatio);
      }
    }

    return totalBilled.value();
  }

}
