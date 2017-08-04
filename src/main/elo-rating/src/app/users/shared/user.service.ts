import { User } from './user.model';
import { Http } from '@angular/http';
import { environment } from './../../../environments/environment';
import { Injectable } from '@angular/core';
import 'rxjs/add/operator/toPromise';


@Injectable()
export class UserService {
  private url = environment.serverUrl;
  private headers = new Headers({ 'Content-Type': 'application/json' });

  constructor(private http: Http) { }

  signIn(idToken: string): Promise<User> {
    let url = `${this.url}/users/sign-in`;
    return this.http.post(url, idToken)
      .toPromise()
      .then(response => response.json() as User)
      .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occured', error);
    return Promise.resolve(null);
  }
}