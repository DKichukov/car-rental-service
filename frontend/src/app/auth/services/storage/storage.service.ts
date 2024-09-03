import { Injectable } from "@angular/core";

const TOKEN = "token";
const USER = "user";

@Injectable({
  providedIn: "root",
})
export class StorageService {
  constructor() {}

  static saveToken(token: string) {
    window.localStorage.removeItem(TOKEN);
    window.localStorage.setItem(TOKEN, token);
  }

  static saveUser(user: any) {
    window.localStorage.removeItem(USER);
    window.localStorage.setItem(USER, JSON.stringify(user));
  }

  static getToken() {
    return window.localStorage.getItem(TOKEN);
  }

  static getUser() {
    const storedUser = localStorage.getItem(USER);
    return storedUser ? JSON.parse(storedUser) : undefined;
  }

  static getUserRole(): string {
    const storedUser = this.getUser();
    if (storedUser == null) return "";
    return storedUser.role;
  }

  static isAdminLoggedIn(): boolean {
    if (this.getToken() == null) return false;
    const role: string = this.getUserRole();
    return role == "ADMIN";
  }
  
    static isCustomerLoggedIn(): boolean {
    if (this.getToken() == null) return false;
    const role: string = this.getUserRole();
    return role == "CUSTOMER";
  }
}
