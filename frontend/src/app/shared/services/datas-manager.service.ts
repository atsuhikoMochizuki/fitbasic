import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DatasManagerService {
  isAccountActivationSucceed = new BehaviorSubject<boolean>(false);
  
}
