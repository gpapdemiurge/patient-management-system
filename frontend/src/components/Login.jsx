import { LoginForm } from './login-form';

export default function Login() {
  return (
    <div className="flex min-h-svh w-full flex-col px-6 pb-16 pt-10 md:px-10 md:pb-20 md:pt-12">
      <h1 className="w-full text-center text-3xl font-semibold leading-tight tracking-tight text-foreground sm:text-4xl lg:text-5xl">
        Patient Managment System
      </h1>
      <div className="flex w-full flex-1 items-center justify-center py-8">
        <div className="w-full max-w-sm">
          <LoginForm />
        </div>
      </div>
    </div>
  );
}

