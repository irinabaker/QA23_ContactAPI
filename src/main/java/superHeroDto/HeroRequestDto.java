package superHeroDto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class HeroRequestDto {

    String birthDate;
    String city;
    String fullName;
    String gender;
    String mainSkill;
    String phone;
}
