import { useParams } from 'react-router-dom';

export default function DoctorDashboard() {
  const { id } = useParams();
  return (
    <div className="p-6">
      <h1 className="text-2xl">Doctor Dashboard</h1>
      <p>Welcome, doctor {id} — doctor's features will be here.</p>
    </div>
  );
}
