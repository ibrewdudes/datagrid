package org.rlnieto.pruebas.model;

import lombok.*;

/**
 * Used to report the result of a method call
 *
 */
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class OpStatus {
    // True if ok, false if not
    private @NonNull boolean ok;
    // Information about the error or the operation (if not error)
    private String text;

}
