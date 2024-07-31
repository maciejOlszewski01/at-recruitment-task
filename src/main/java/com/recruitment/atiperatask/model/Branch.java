package com.recruitment.atiperatask.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Branch {

    private String name;
    private Commit commit;
}
