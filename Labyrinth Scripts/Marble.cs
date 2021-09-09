using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Marble : MonoBehaviour
{
    private Rigidbody marbleRb;
    private GameObject marble;
    private GameManager gameManager;
    public Collider marbleCollider;
    public AudioSource collisionSound;

    //sets rigid body and audio for marble
    void Start()
    {
        marbleRb = GetComponent<Rigidbody>();
        marble = GameObject.Find("Marble");
        collisionSound = GetComponent<AudioSource>();
        gameManager = GameObject.Find("Game Manager").GetComponent<GameManager>();
    }

    void Update()
    {

    }

    //determines what action to take when ball colides and activates appropriate state
    private void OnTriggerEnter(Collider other)
    {
        if (other.CompareTag("Hole"))
        {
            marbleCollider.enabled = !marbleCollider.enabled;
            gameManager.Lose();

        }

        if (other.CompareTag("Wall"))
        {
            collisionSound.Play();
        }

        if (other.CompareTag("End"))
        {
            gameManager.Win();
            
        }
    }
}