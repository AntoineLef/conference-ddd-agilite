package ca.ulaval.glo4003.ddd.ws.domain.md;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class BillingService {

  private static final double DAILY_WORKED_HOURS = 8.0;

  private DoctorRepository doctorRepository;
  private ProcedureRepository procedureRepository;

  public BillingService(DoctorRepository doctorRepository,
                        ProcedureRepository procedureRepository)
  {
    this.doctorRepository = doctorRepository;
    this.procedureRepository = procedureRepository;
  }

  public void addNewProcedure(String doctorId, ProcedureInfo procedureInfo) {
    procedureRepository.add(new Procedure(doctorId,
                                          procedureInfo.hospitalName,
                                          procedureInfo.startTime,
                                          procedureInfo.endTime));
  }

  public void addDoctor(Doctor doctor) {
    doctorRepository.save(doctor);
  }

  public double dailyTotalOf(String doctorId, LocalDate wantedDate) {
    Double total = 0.0;

    List<Procedure> procedures = procedureRepository.findAll();

    for (Procedure procedure : procedures) {
      if (procedure.getDoctorId().equals(doctorId)) {
        LocalDateTime startTime = procedure.getStartTime();
        LocalDateTime endTime = procedure.getEndTime();
        Period period = new Period(startTime, endTime);

        if (period.isForDate(wantedDate)) {

          Duration procedureDuration = period.calculateDuration();

          double procedureRatio = procedureDuration.toHours()
                                  / DAILY_WORKED_HOURS;
          total += 2000 * procedureRatio;
        }
      }
    }

    return total;
  }

}
