package com.elg.vshop.validator.date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class DOBValidator implements ConstraintValidator<ValidDOB, Date> {
   public void initialize(ValidDOB constraint) {
   }

   public boolean isValid(Date obj, ConstraintValidatorContext context) {
      Date dateToday = new Date();
      LocalDate localDateToday = convertToLocalDate(dateToday);
      LocalDate dob = convertToLocalDate(obj);
      return calculateAge(dob, localDateToday) >= 18;
   }

   private int calculateAge(LocalDate birthDate, LocalDate currentDate) {
      if ((birthDate != null) && (currentDate != null)) {
         return Period.between(birthDate, currentDate).getYears();
      } else {
         return 0;
      }
   }

   private LocalDate convertToLocalDate(Date dateToConvert) {
      return dateToConvert.toInstant()
              .atZone(ZoneId.systemDefault())
              .toLocalDate();
   }
}
