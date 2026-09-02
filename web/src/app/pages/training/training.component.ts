import { Component } from '@angular/core';

@Component({
  selector: 'app-training',
  standalone: true,
  template: `
    <section class="placeholder-page">
      <div>
        <p>Royal Arc en Ciel CTT</p>
        <h1>Entraînements</h1>
        <span>Cette page sera construite dans une prochaine étape.</span>
      </div>
    </section>
  `,
  styles: [`
    .placeholder-page {
      min-height: 55vh;
      display: grid;
      place-items: center;
      padding: 48px 20px;
      text-align: center;
      background: #f7f9fc;
    }

    p {
      margin: 0 0 8px;
      color: #217ce7;
      font-weight: 800;
      text-transform: uppercase;
      letter-spacing: .1em;
      font-size: .78rem;
    }

    h1 {
      margin: 0 0 10px;
      color: #17283c;
      font-size: 2.2rem;
    }

    span {
      color: #67788b;
    }
  `]
})
export class TrainingComponent {}
