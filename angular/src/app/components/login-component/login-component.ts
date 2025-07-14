import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { GenericFetchService } from '../../service/fetch/generic-fetch-service';
import { AuthService } from '../../service/auth/auth-service';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { CommonModule } from '@angular/common';
import { LoginRequest } from '../../model/dto/LoginRequest';
import { LoginResponse } from '../../model/dto/LoginResponse';
import { AppConstants } from '../../const/app-constants';

@Component({
  selector: 'app-login-component',
  imports: [FormsModule, ReactiveFormsModule, CommonModule],
  standalone: true,
  templateUrl: './login-component.html',
  styleUrl: './login-component.css'
})
export class LoginComponent implements OnInit, OnDestroy {
  private genericFetchService = inject(GenericFetchService);
  private authService = inject(AuthService);
  private router = inject(Router);

  private loginApi = this.genericFetchService.createPostApiState<LoginRequest, LoginResponse>(AppConstants.API_BASE_URL + '/api/auth/login');
  loginState$ = this.loginApi.state$;
  private stateSubscription?: Subscription;

  loginForm = new FormGroup({
    identificador: new FormControl('', Validators.required),
    password: new FormControl('', [Validators.required]),
  });

  ngOnInit(): void {
    this.stateSubscription = this.loginState$.subscribe(state => {
      if (state.success && state.data?.token && state.data?.avatar) {
        this.authService.login(state.data.token, state.data.avatar);
        this.router.navigate(['/profile/index']);
      }
    });
  }

  ngOnDestroy(): void {
    this.stateSubscription?.unsubscribe();
    this.loginApi.reset();
  }

  handleSubmit() {
    if (this.loginForm.invalid) {
      return;
    }
    
    const loginData = this.loginForm.value as LoginRequest;
    this.loginApi.post(loginData);
  }

}
