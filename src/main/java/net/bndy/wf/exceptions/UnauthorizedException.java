package net.bndy.wf.exceptions;

public class UnauthorizedException extends AppException {

    public UnauthorizedException() {
        super("error.unauthorizedAccess");
    }
}
